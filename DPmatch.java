package in.andante.drawbm;

import java.util.regex.Pattern;

import android.graphics.Point;

//DPmatching処理

public class DPmatch {
    private final static int INFINITY = 0xffffff;
    private final static int POINT_MAX = 64;
    private final static int WINDOW_WIDTH_MIN = 3;
    private final static int COORD_WEIGHT = 1;
 
    int calcDistance(Pattern inp, Pattern dict, int thres) {
      int inpLen = inp.segments.length;
      int dictLen = dict.segments.length;
      int[] g = new int[POINT_MAX];
      int inpWin, dictWin;
      int start, end;
      int g_ij_1, minDist, d;
      int a0, a1, a2;
      int inpIdx, dictIdx;
      int i, j;
  
      if(inpLen >= POINT_MAX || dictLen >= POINT_MAX) {
        return INFINITY;
      }
      // 初期化
      for(i=0; i<=dictLen; i++) {
        g[i] = INFINITY;
      }
      // 窓幅決定
      if(inpLen > dictLen) {
        inpWin = Math.max(inpLen - dictLen, WINDOW_WIDTH_MIN);
        dictWin = WINDOW_WIDTH_MIN;
      }
      else {
        inpWin = WINDOW_WIDTH_MIN;
        dictWin = Math.max(dictLen - inpLen, WINDOW_WIDTH_MIN);
      }
      // 枝狩り閾値
      thres *= (inpLen + dictLen);
  
      g[0] = 0;
      g_ij_1 = INFINITY;
      for(i=1, inpIdx=0; i<=inpLen; i++, inpIdx++) {
        start = Math.max(i - inpWin, 1);
        end   = Math.min(i + dictWin, dictLen);
        g_ij_1 = INFINITY;
        minDist = INFINITY;
        j = start;
        for(dictIdx=start-1; j<=end; j++, dictIdx++) {
          d = distance(inp.points[inpIdx], inp.segments[inpIdx], dict.points[dictIdx], dict.segments[dictIdx]);
          a0 = g_ij_1 + d;
          a1 = g[j-1] + 2 * d;
          a2 = g[j] + d;
          g[j-1] = g_ij_1;
          g_ij_1 = Math.min(Math.min(a0, a1), a2);
          minDist = Math.min(minDist, g_ij_1);
        }
        g[j-1] = g_ij_1;
        //　閾値を超えた場合, 計算を中断
        if(minDist > thres) {
          return INFINITY;
        }   
      }
      // トータル距離を正規化
      return (g_ij_1 / (inpLen + dictLen));
    }
  
    // 特徴点間の距離d(i, j)を計算
    private int distance(Point inpPo, Segment inpSeg, Point dictPo, Segment dictSeg) {
      // 座標点間の距離
      int cdist = (int)Math.sqrt((inpPo.x - dictPo.x)^2 + (inpPo.y - dictPo.y)^2);
      // 重み付け距離
      return (COORD_WEIGHT * cdist);
   }
 }