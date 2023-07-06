package cn.edu.heuet.editspinner;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SimpleAdapter
 *
 * @author WrBug
 * @since 2017/2/25
 */
public class SimpleAdapter extends BaseEditSpinnerAdapter implements EditSpinnerFilter {
    private final Context mContext;
    private final List<String> mSpinnerData;
    private final List<String> mCacheData;
    private final int[] indexs;

    public SimpleAdapter(Context context, List<String> spinnerData) {
        this.mContext = context;//用于获取应用程序的上下文环境。
        this.mSpinnerData = spinnerData;//于存储Spinner的数据项
        mCacheData = new ArrayList<>(spinnerData);//于在Spinner数据项过滤时缓存原始数据
        indexs = new int[mSpinnerData.size()];//一个长度与mSpinnerData相同的整型数组
    }

    @Override
    public EditSpinnerFilter getEditSpinnerFilter() {
        return this;
    }

    @Override
    public String getItemString(int position) {
        return mSpinnerData.get(indexs[position]);
    }

    @Override
    public int getCount() {
        return mCacheData == null ? 0 : mCacheData.size();
    }

    @Override
    public String getItem(int position) {
        return mCacheData == null ? "" : mCacheData.get(position) == null ? "" : mCacheData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        if (convertView == null) {
            textView = (TextView) LayoutInflater.from(mContext).inflate(android.R.layout.simple_spinner_item, null);
        } else {
            textView = (TextView) convertView;
        }
        textView.setText(Html.fromHtml(getItem(position)));
        return textView;
    }

    /**
     * 实现动态搜索的核心逻辑
     */
    @Override
    public boolean onFilter(String keyword) {
        mCacheData.clear();//清空列表，方便重新填充过滤后的数据
        // 空值匹配全部
        if (TextUtils.isEmpty(keyword.trim())) {//判断关键字keyword是否为空或只包含空格
            mCacheData.addAll(mSpinnerData);
            for (int i = 0; i < indexs.length; i++) {//表示需要显示所有的数据项
                // 将mSpinnerData中的所有数据项添加到mCacheData中，并将indexs数组重新初始化为索引值的顺序
                indexs[i] = i;
            }
        }
        // 实现全量模糊匹配（优先匹配原词）
        else {
            StringBuilder builder = new StringBuilder();
            String[] keywordSplit = keyword.split("");
            builder.append(".*");
            for (String word : keywordSplit) {
                if (TextUtils.isEmpty(word)) {
                    continue;
                }
                word = replaceMetacharacter(word);
                builder.append(word).append(".*");
            }

            Pattern originPattern = Pattern.compile(replaceMetacharacter(keyword));//匹配原始关键字
            Pattern fullPattern = Pattern.compile(builder.toString());//全量匹配

            for (int i = 0; i < mSpinnerData.size(); i++) {//遍历
                String itemStr = mSpinnerData.get(i);
                Matcher originMatcher = originPattern.matcher(itemStr);
               /*
               优先匹配原词
               比如，在 2223444 中匹配 234
               优先匹配 22 '234' 44
               而不是  '2' 22 '34' 44
                */
                if (originMatcher.find()) {
                    coloringItemStr(true, i, itemStr, keyword);
                }
                // 匹配不到原词的话，全量最左寻找
                else {
                    Matcher fullMatcher = fullPattern.matcher(itemStr);
                    if (fullMatcher.find()) {
                        coloringItemStr(false, i, itemStr, keyword);
                    }
                }
            }
        }
        notifyDataSetChanged();
        return mCacheData.size() <= 0;
    }

    private void coloringItemStr(boolean origin, int i, String itemStr, String keyword) {
        indexs[mCacheData.size()] = i;
        StringBuilder colorStr = new StringBuilder();
        if (origin) {
            colorStr.append(itemStr.replaceFirst(keyword, "<font color=\"#aa0000\">" + keyword + "</font>"));
        } else {
            indexs[mCacheData.size()] = i;
            String beforeMatchedIndexStr = "";
            String afterMatchedIndexStr = "";

            int matchedStrLen = itemStr.length();
            String[] keywordSplit = keyword.split("");
            for (String highLightStr : keywordSplit) {
                if (TextUtils.isEmpty(highLightStr)) {
                    continue;
                }
                int index = itemStr.indexOf(highLightStr);
                if (index < matchedStrLen - 1) {
                    beforeMatchedIndexStr = itemStr.substring(0, index + 1);
                    afterMatchedIndexStr = itemStr.substring(index + 1);
                    itemStr = afterMatchedIndexStr;
                }
                if (!TextUtils.isEmpty(beforeMatchedIndexStr)) {
                    colorStr.append(beforeMatchedIndexStr.replaceFirst(highLightStr, "<font color=\"#aa0000\">" + highLightStr + "</font>"));
                }
            }
            colorStr.append(afterMatchedIndexStr);
        }
        mCacheData.add(colorStr.toString());
    }

    private String replaceMetacharacter(String keyword) {
        StringBuilder transferredKeyword = new StringBuilder();
        for (int i = 0, len = keyword.length(); i < len; i++) {
            String ch = String.valueOf(keyword.charAt(i));
            switch (ch) {
                case "\\":
                    ch = "\\\\";
                    break;
                case "^":
                    ch = "\\^";
                    break;
                case "$":
                    ch = "\\$";
                    break;
                case "*":
                    ch = "\\*";
                    break;
                case "+":
                    ch = "\\+";
                    break;
                case "?":
                    ch = "\\?";
                    break;
                case "{":
                    ch = "\\{";
                    break;
                case "}":
                    ch = "\\}";
                    break;
                case "[":
                    ch = "\\[";
                    break;
                case "]":
                    ch = "\\]";
                    break;
                case "(":
                    ch = "\\(";
                    break;
                case ")":
                    ch = "\\)";
                    break;
                case "|":
                    ch = "\\|";
                    break;
                case ".":
                    ch = "\\.";
                    break;
                case "!":
                    ch = "\\!";
                    break;
                default:
            }
            transferredKeyword.append(ch);
        }
        return transferredKeyword.toString();

    }
}
