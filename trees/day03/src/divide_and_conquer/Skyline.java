package divide_and_conquer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Skyline {

    public static class Point {
        public int x;
        public int y;
        private Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static class Building {
        private int l, r, h;
        public Building(int l, int r, int h) {
            this.l = l;
            this.r = r;
            this.h = h;
        }
    }

    public static Point[] merge_skyline(Point[] left, Point[] right) {
        Point[] result = new Point[left.length+right.length];
        int left_height = 0;
        int right_height = 0;
        int left_index = 0;
        int right_index = 0;
        int result_index = 0;
        while (left_index < left.length && right_index < right.length) {
            if (left[left_index].x < right[right_index].x) {
                left_height = left[left_index].y;
                int curr_y = Math.max(left_height,right_height);
                if (result_index == 0 || curr_y != result[result_index-1].y) {
                    result[result_index] = new Point(left[left_index].x, curr_y);
                    result_index++;
                }
                left_index++;
            } else {
                right_height = right[right_index].y;
                int curr_y = Math.max(left_height,right_height);
                if (result_index == 0 || curr_y != result[result_index-1].y) {
                    result[result_index] = new Point(right[right_index].x, curr_y);
                    result_index++;
                }
                right_index++;
            }
        }
        while (left_index < left.length) {
            right_height = 0;
            left_height = left[left_index].y;
            int curr_y = Math.max(left_height,right_height);
            if (result_index == 0 || curr_y != result[result_index-1].y) {
                result[result_index] = new Point(left[left_index].x, curr_y);
                result_index++;
            }
            left_index++;
        }
        while (right_index < right.length) {
            left_height = 0;
            right_height = right[right_index].y;
            int curr_y = Math.max(left_height,right_height);
            if (result_index == 0 || curr_y != result[result_index-1].y) {
                result[result_index] = new Point(right[right_index].x, curr_y);
                result_index++;
            }
            right_index++;
        }
        for (int i = 0; i<result_index-1;i++) {
            if (result[i].x == result[i+1].x) {
                int rm_index = 0;
                if (result[i].y > result[i+1].y) {
                    rm_index = i+1;
                } else {
                    rm_index = i;
                }
                Point[] prev = Arrays.copyOfRange(result,0,rm_index);
                Point[] post = Arrays.copyOfRange(result, rm_index+1, result_index);
                result_index--;
                result = new Point[prev.length+post.length];
                System.arraycopy(prev,0,result, 0, prev.length);
                System.arraycopy(post,0, result, rm_index, post.length);
            }
        }
        return Arrays.copyOfRange(result, 0, result_index);
    }

    public static Point[] skyline_sort(Point[] arr) {
        if (arr.length == 2) {
            return arr;
        }
        Point[] left = Arrays.copyOfRange(arr,0, arr.length / 4 * 2);
        Point[] right = Arrays.copyOfRange(arr, arr.length / 4 * 2, arr.length);
        left = skyline_sort(left);
        right = skyline_sort(right);
        return merge_skyline(left, right);
    }

    // Given an array of buildings, return a list of points representing the skyline
    public static List<Point> skyline(Building[] B) {
        Point[] arr = new Point[B.length * 2];
        for (int i=0;i<B.length;i++) {
            arr[i*2] = new Point(B[i].l, B[i].h);
            arr[i*2+1] = new Point(B[i].r, 0);
        }
        arr = skyline_sort(arr);
        return Arrays.asList(arr);
    }
}
