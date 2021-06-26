package models;

import java.util.Random;


public class Erlang {
    private static final int k = 2;
    private static final Random random = new Random();
    private static final double e = 2.718281828d;

    public Erlang() {

    }

    public int getValue(float lambda){
        float x = random.nextFloat();
        float exp1 = (float) Math.pow(lambda,this.k);
        float exp2 = (float) Math.pow(x,this.k - 1);
        float exp3 = (float) Math.pow(e,lambda*x*(-1));
        int result = Math.round((exp1*exp2*exp3)/factorial(this.k -1));
        return result;
    }

    public int factorial(int fac){
        int num = 1;
        for(int i=1;i<=num;i++) {
            num = num * i;
        }
        return num;
    }
}
