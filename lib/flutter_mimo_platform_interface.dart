import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'flutter_mimo_method_channel.dart';

abstract class FlutterMimoPlatform extends PlatformInterface {
  /// Constructs a FlutterMimoPlatform.
  FlutterMimoPlatform() : super(token: _token);

  static final Object _token = Object();

  static FlutterMimoPlatform _instance = MethodChannelFlutterMimo();

  /// The default instance of [FlutterMimoPlatform] to use.
  ///
  /// Defaults to [MethodChannelFlutterMimo].
  static FlutterMimoPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [FlutterMimoPlatform] when
  /// they register themselves.
  static set instance(FlutterMimoPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
