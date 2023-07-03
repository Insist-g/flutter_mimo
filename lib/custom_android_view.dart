import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

const String LoadFailed = "LoadFailed";
const String Dismiss = "Dismiss";

typedef OnViewCreated = Function(CustomViewController);

///自定义AndroidView
class CustomAndroidView extends StatefulWidget {
  final OnViewCreated onViewCreated;

  const CustomAndroidView(this.onViewCreated, {Key? key}) : super(key: key);

  @override
  State<CustomAndroidView> createState() => _CustomAndroidViewState();
}

class _CustomAndroidViewState extends State<CustomAndroidView> {
  late MethodChannel _channel;

  @override
  Widget build(BuildContext context) {
    print("build");
    return _getPlatformFaceView();
  }

  Widget _getPlatformFaceView() {
    return AndroidView(
      viewType: 'com.rex.custom.android/customView',
      onPlatformViewCreated: _onPlatformViewCreated,
      creationParams: const <String, dynamic>{'initParams': 'hello world'},
      creationParamsCodec: const StandardMessageCodec(),
    );
  }

  void _onPlatformViewCreated(int id) {
    _channel = MethodChannel('com.rex.custom.android/customView$id');
    final controller = CustomViewController._(
      _channel,
    );
    widget.onViewCreated(controller);
  }
}

class CustomViewController {
  final MethodChannel _channel;
  final StreamController<String> _controller = StreamController<String>();

  CustomViewController._(
    this._channel,
  ) {
    _channel.setMethodCallHandler(
      (call) async {
        switch (call.method) {
          case 'getMessageFromAndroidView':
            // 从native端获取数据
            final result = call.arguments as String;
            _controller.sink.add(result);
            break;
          // case 'initMimoSdk':
          //   // 从native端获取数据
          //   final result = call.arguments as String;
          //   _controller.sink.add(result);
          //   break;
          // case 'loadBannerAd':
          //   // 从native端获取数据
          //   final result = call.arguments as String;
          //   _controller.sink.add(result);
          //   break;
          // case 'showBannerAd':
          //   // 从native端获取数据
          //   final result = call.arguments as String;
          //   _controller.sink.add(result);
          //   break;
        }
      },
    );
  }

  Stream<String> get customDataStream => _controller.stream;

  // 发送数据给native
  Future<void> sendMessageToAndroidView(String message) async {
    await _channel.invokeMethod(
      'getMessageFromFlutterView',
      message,
    );
  }

  ///isDebug false 调试模式
  Future<bool> initMimoSdk({bool isDebug = false}) async {
    return await _channel.invokeMethod('initMimoSdk', isDebug);
  }

  ///加载Banner广告
  ///加载成功后 自动调用 showBannerAd
  Future<void> loadBannerAd() async {
    await _channel.invokeMethod(
      'loadBannerAd',
    );
  }

  ///展示Banner广告
  Future<void> showBannerAd() async {
    await _channel.invokeMethod(
      'showBannerAd',
    );
  }

  ///加载插画广告
  ///加载成功后 自动调用 showInterstitialAd
  Future<void> loadInterstitialAd() async {
    await _channel.invokeMethod(
      'loadInterstitialAd',
    );
  }

  ///展示插画广告
  Future<void> showInterstitialAd() async {
    await _channel.invokeMethod(
      'showInterstitialAd',
    );
  }

  ///销毁
  Future<void> dispose() async {
    await _channel.invokeMethod(
      'dispose',
    );
  }

}
