import 'package:flutter_test/flutter_test.dart';
import 'package:flutter_mimo/flutter_mimo.dart';
import 'package:flutter_mimo/flutter_mimo_platform_interface.dart';
import 'package:flutter_mimo/flutter_mimo_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockFlutterMimoPlatform
    with MockPlatformInterfaceMixin
    implements FlutterMimoPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final FlutterMimoPlatform initialPlatform = FlutterMimoPlatform.instance;

  test('$MethodChannelFlutterMimo is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelFlutterMimo>());
  });

  test('getPlatformVersion', () async {
    FlutterMimo flutterMimoPlugin = FlutterMimo();
    MockFlutterMimoPlatform fakePlatform = MockFlutterMimoPlatform();
    FlutterMimoPlatform.instance = fakePlatform;

    expect(await flutterMimoPlugin.getPlatformVersion(), '42');
  });
}
