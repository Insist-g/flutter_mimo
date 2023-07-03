
import 'flutter_mimo_platform_interface.dart';

class FlutterMimo {
  Future<String?> getPlatformVersion() {
    return FlutterMimoPlatform.instance.getPlatformVersion();
  }
}
