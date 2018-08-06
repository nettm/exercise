package com.nettm.exercise.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class NettyEncoder extends MessageToByteEncoder<ByteBuf> {

    @Override
    protected ByteBuf allocateBuffer(ChannelHandlerContext ctx, ByteBuf msg, boolean preferDirect) throws Exception {
        // TODO Auto-generated method stub
        return super.allocateBuffer(ctx, msg, preferDirect);
    }

    @Override
    protected void encode(ChannelHandlerContext arg0, ByteBuf arg1, ByteBuf arg2) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    protected boolean isPreferDirect() {
        // TODO Auto-generated method stub
        return super.isPreferDirect();
    }

}
