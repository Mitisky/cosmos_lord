package netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * This class created on 2017/12/2.
 *
 * @author Connery
 */
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

  @Override
  public void channelActive(ChannelHandlerContext ctx) {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < 10; i++) {
      sb.append(i).append(",");
    }
    ctx.write(Unpooled.copiedBuffer("a",
        CharsetUtil.UTF_8));
    ctx.write(Unpooled.copiedBuffer("b",
        CharsetUtil.UTF_8));
    ctx.flush();
    ctx.write(Unpooled.copiedBuffer("a",
        CharsetUtil.UTF_8));
    ctx.write(Unpooled.copiedBuffer("b",
        CharsetUtil.UTF_8));
    ctx.flush();
//        ctx.writeAndFlush(Unpooled.copiedBuffer("a",
//                CharsetUtil.UTF_8));
//
//        ctx.writeAndFlush(Unpooled.copiedBuffer("Next",
//                CharsetUtil.UTF_8));
//
//        ctx.writeAndFlush(Unpooled.copiedBuffer(sb.toString(),
//                CharsetUtil.UTF_8));
//
//        ctx.writeAndFlush(Unpooled.copiedBuffer("end",
//                CharsetUtil.UTF_8));
  }

  protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf)
      throws Exception {
    System.out.println(
        "Client received: " + byteBuf.toString(CharsetUtil.UTF_8));
  }

  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    System.out.println("Finish");
  }

  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    cause.printStackTrace();
    ctx.close();
  }
}
