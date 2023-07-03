import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'flutter_mimo_platform_interface.dart';

/// An implementation of [FlutterMimoPlatform] that uses method channels.
class MethodChannelFlutterMimo extends FlutterMimoPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('flutter_mimo');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }
}
