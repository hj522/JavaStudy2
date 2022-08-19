package kr.or.ddit.basic;

import java.util.Random;
import javax.swing.JOptionPane;

/*
        컴퓨터와 가위바위보를 진행하는 프로그램을 작성하시오
 */

public class T07ThreadGame { // 메인스레드

   public static boolean inputCheck = false;
   
   public static void main(String[] args) {
      
      DataInput2 input = new DataInput2();
      CountDown2 count = new CountDown2();
      
      input.start();
//      count.start();
      
          for(int i=5; i>=1; i--) {
              
              if(T07ThreadGame.inputCheck) {
                 break;
              }
              
              System.out.println(i);
              
              try {
                 Thread.sleep(1000);
              } catch (InterruptedException e) {
                 e.printStackTrace();
           }
        }
      
      
      String[] cpu = {"가위", "바위", "보"};
      Random random = new Random();
      String computer = cpu[random.nextInt(3)];
      
      String result = " ";
      
      if(input.str.equals(computer)) {
         result = ("=== 결과 === \n컴퓨터: " + computer + "\n당 신: " + input.str + "\n결과: 무승부입니다.");
         System.out.println(result);
      } else if ((computer.equals("보") && input.str.equals("가위"))
            || (computer.equals("바위") && input.str.equals("보"))
            || (computer.equals("가위") && input.str.equals("바위"))) {
         result = ("=== 결과 === \n컴퓨터: " + computer + "\n당 신: " +  input.str + "\n결과: 당신이 이겼습니다.");
         System.out.println(result);
      } else {
         result = ("=== 결과 === \n컴퓨터: " + computer + "\n당 신: " +  input.str + "\n결과: 컴퓨터가 이겼습니다.");
         System.out.println(result);
      }
   }
}


class DataInput2 extends Thread { //입력 스레드
   public static String str=" ";
   @Override
   public void run() {
      str = JOptionPane.showInputDialog("가위바위보~!");
//      System.out.println(str);
      T07ThreadGame.inputCheck = true; // 입력확인
      
   }
}

class CountDown2 extends Thread { //카운트다운 스레드 
   
   @Override
   public void run() {
      

      System.out.println("5초가 지났습니다. 컴퓨터가 이겼습니다.");
      System.exit(0);
   }
}