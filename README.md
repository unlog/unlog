[UnLog](https://github.com/unlog/unlog/wiki)
============================================

An (almost) invisible logging framework

UnLog is a logging framework that lets you do your logging without requiring every class to depend on a logging
framework.  You define the interface to your log and UnLog implements that interface using dynamic proxies.  Behind
the scenes, UnLog will publish your log events to whatever log framework you like.  (You might have to implement your
own LogPublisher if there isn't one already there.)

UnLog Core is completely free of any non-jdk dependencies.  It supports publishing of log events via java.util.logging.

Future support for other logging frameworks will be released in separate modules to maintain the independence of UnLog
 Core.
