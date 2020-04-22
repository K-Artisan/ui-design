package com.arzirtime.support.util;

import java.util.List;

public class ListUtils {

    /**
     * 对数组进行分页：
     * 返回值：超过数组范围，返回null
     */
    public static <T> List<T> getPage(List<T> src, int pageIndex, int pageSize) {
        List result = null;

        int totalCount = src.size();
        int totalPage = getTotalPage(totalCount, pageSize);

        if (pageIndex > totalPage) {
            return null;
        } else {
            int start = (pageIndex - 1) * pageSize;
            int end = (pageIndex == totalPage) ? totalCount : pageIndex * pageSize;

            result = src.subList(start, end);
        }

        return result;
    }

    /**
     * 获取总页数
     */
    public static <T> int getTotalPage(int totalCount, int pageSize) {
        int totalPage = (int)Math.ceil((double) totalCount/pageSize);
        return totalPage;
    }

    /**
     * 获取当前页（如果是最后一页，即使是pageIndex在增加，总是取实际最后一页）
     */
    public static <T> int getAutoFixCurrentPageIndex(int totalCount, int pageIndex, int pageSize) {
        int totalPage = getTotalPage(totalCount, pageSize);
        int currPageIndex = pageIndex >= totalPage ? totalPage : pageIndex;
        return currPageIndex;
    }
}
