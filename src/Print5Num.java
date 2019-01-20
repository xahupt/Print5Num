import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 *
 * @author percy 　按顺序打印数字利用ＲｅｅｎｔｒａｎＬｏｃｋ和Ｃｏｎｄｉｔｉｏｎ
 *
 */
public class Print5Num {
    ReentrantLock lock = new ReentrantLock();
    Condition     c1   = lock.newCondition();
    Condition     c2   = lock.newCondition();
    Condition     c3   = lock.newCondition();
    int flag,state=1;
    void PrintC1(){Print(1,c1,c2);}
    void PrintC2(){Print(0,c2,c3);}
    void PrintC3(){Print(2,c3,c1);}
    void Print(int flag,Condition RecentLock,Condition Next_Lock){
        for (;;){
            try{
                lock.lock();
                //System.out.println(Thread.currentThread().getName()+":"+lock.toString());
                if (state%3==flag){

                    for (int i=0;i<5;i++){
                        System.out.println(Thread.currentThread().getName()+":"+state);
                        state++;
                    }
                    try {
                        Next_Lock.signal();             //为何要先用ｓｉｇｎａｌ
                        RecentLock.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

                if (state>75){
                    return;
                }
            }finally {
                lock.unlock();

            }



        }
    }
    public static void main(String[] args){
        Print5Num print5Num = new Print5Num();
        new Thread(print5Num::PrintC1,"A").start();
        new Thread(print5Num::PrintC2,"B").start();
        new Thread(print5Num::PrintC3,"C").start();
    }
}
