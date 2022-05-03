package ru.example.emulator.models;

import com.ibm.mq.MQQueue;

public class Queues {

    MQQueue bankRequestQueue;

    MQQueue bankResponseQueue;

    public MQQueue getBankRequestQueue() {
        return bankRequestQueue;
    }

    public void setBankRequestQueue(MQQueue bankRequestQueue) {
        this.bankRequestQueue = bankRequestQueue;
    }

    public MQQueue getBankResponseQueue() {
        return bankResponseQueue;
    }

    public void setBankResponseQueue(MQQueue bankResponseQueue) {
        this.bankResponseQueue = bankResponseQueue;
    }
}
