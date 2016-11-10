package com.synaric.app.player.event;

import com.synaric.common.entity.AudioInfo;

/**
 * 播放器状态更新事件，通过Hermes-EventBus（一个EventBus的IPC实现）分发。
 * <br/><br/>Created by Synaric on 2016/11/9 0009.
 */
public class PlayerStateChangedEvent {

    public int state;
    public int type;
    public AudioInfo info;

    public PlayerStateChangedEvent(int state, int type, AudioInfo info) {
        this.state = state;
        this.type = type;
        this.info = info;
    }
}
