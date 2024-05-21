import json
import pymysql
import re
import requests
import warnings


# pip install pysnowflake -i https://pypi.tuna.tsinghua.edu.cn/simple/
# pip install pandas requests json re numpy pymysql warnings
# https://blog.csdn.net/qq_57344778/article/details/123430844

class get_data():

    def __init__(self, dic, url):
        self.url = url
        self.dic = dic

    def get_url(self, lid, page, pageid=153):
        return self.url + "&lid=" + str(lid) + "&pageid=" + str(pageid) + "&page=" + str(page)

    def get_json_url(self, url):
        out = []
        json_req = requests.get(url)
        user_dict = json.loads(json_req.text)
        print(url)
        for dic in user_dict["result"]["data"]:
            out.append([dic["url"], dic['intro'], dic['title']])

        return out

    def getfind_data(self, list1, label):
        out_date = []

        for line in list1:
            try:
                req = requests.get(line[0])
                req.encoding = "utf-8"
                req = req.text
                content = re.findall('<!-- 行情图end -->.*<!-- news_keyword_pub', req, re.S)
                if len(content) != 0:
                    pass
                else:
                    content = re.findall('<!-- 正文 start -->.*<!--', req, re.S)
                if len(content) != 0:
                    pass
                else:
                    content = re.findall('<!--新增众测推广文案-->.*?<!-- ', req, re.S)

                print(content)
                if len(line[0].split("/")[3]) == 10:
                    out_date.append([line[0].split("/")[3], line[2], line[1], content, label, line[0]])

                elif len(line[0].split("/")[4]) == 10:
                    out_date.append([line[0].split("/")[4], line[2], line[1], content, label, line[0]])


                elif len(line[0].split("/")[5]) == 10:
                    out_date.append([line[0].split("/")[5], line[2], line[1], content, label, line[0]])

                elif len(line[0].split("/")[6]) == 10:
                    out_date.append([line[0].split("/")[6], line[2], line[1], content, label, line[0]])
            except:
                pass

        return out_date

    def main(self):
        warnings.simplefilter(action='ignore', category=UserWarning)
        db_pymysql = pymysql.connect(host='127.0.0.1', port=3306, user='root', password='root',
                                     db='p92g1',
                                     use_unicode=True, charset='utf8')
        mycursor = db_pymysql.cursor()
        out_data_list = []
        for label, lid in self.dic.items():
            for page in range(1, 2):
                he_url = self.get_url(lid, page)
                json_url_list = self.get_json_url(he_url)
                output_data = self.getfind_data(json_url_list, label)
                out_data_list += output_data
        for outdata in out_data_list:
            source_url = outdata[5]
            title = outdata[1]
            source = '新浪新闻'
            enable = '0'
            after_process = '1'
            processing_time = outdata[0]
            content = funtion1('\n'.join(outdata[3]))
            desinfo = outdata[2]
            category_id = outdata[4]
            readcount = 0
            data = (
                source_url, title, source, enable, after_process, processing_time, content,
                desinfo, category_id, readcount)
            mysql = "insert into content_info (source_url, title, source, enable, after_process, processing_time, content, desinfo, category_id, readcount) values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
            mycursor.execute(mysql, data)
            db_pymysql.commit()
        #
        # data = pd.DataFrame(np.array(out_data_list), columns=['时间', '标题', '摘要', '内容', '类别'])
        # data.to_csv("data.csv")
        # data = pd.read_csv("data.csv", index_col=0)
        # # 获取数据内容
        # data["内容"] = data["内容"].apply(funtion1)
        # # 数据去重
        # data = data.drop_duplicates()
        # data = data[[out_data(i) for i in data["时间"].astype(str)]]
        # data.to_csv("pre_data.csv")
        # data[data["内容"].apply(function_)]["类别"].value_counts()


def function_(x):
    if len(x) > 2:
        return True
    else:
        return False


def funtion1(x):
    res1 = ''.join(re.findall('[\u4e00-\u9fa5]*["，","。","！","”","%","“","："]', x[20:]))
    res1 = res1.replace("\"", "")

    res1 = res1.replace("引文", "")
    res1 = res1.replace("正文", "")

    return res1


# 因为对url进行切片可能获取到的不是时间数据所有使用如下函数删掉
def out_data(x):
    if "2023" in x or "2022" in x or "2021" in x:
        return True
    else:
        return False


if __name__ == "__main__":
    data_dic = {"娱乐": 2513,
                "军事": 2514, "财经": 2516,
                "股市": 2517,
                "美股": 2518,
                "国际": 2511}
    url = "https://feed.mix.sina.com.cn/api/roll/get?&k=&num=50"
    get = get_data(data_dic, url)
    get.main()
