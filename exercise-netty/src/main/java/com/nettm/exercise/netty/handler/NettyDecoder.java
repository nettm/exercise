package com.nettm.exercise.netty.handler;

import java.nio.ByteOrder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class NettyDecoder extends LengthFieldBasedFrameDecoder {

    private static final int FRAME_MAX_LENGTH = 16777216;

    public NettyDecoder() {
        super(FRAME_MAX_LENGTH, 0, 4, 0, 4);
    }

    @Override
    protected long getUnadjustedFrameLength(ByteBuf arg0, int arg1, int arg2, ByteOrder arg3) {
        return super.getUnadjustedFrameLength(arg0, arg1, arg2, arg3);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        return super.decode(ctx, in);
    }

}
