import 'dart:math';

import 'package:flutter/material.dart';

import 'package:flutter_mimo/custom_android_view.dart';

void main() {
  runApp(const MaterialApp(home: MyHome()));
}

class MyHome extends StatelessWidget {
  const MyHome({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return const Scaffold(
      body: CustomExample(),
    );
  }
}

class CustomExample extends StatefulWidget {
  const CustomExample({Key? key}) : super(key: key);

  @override
  State<CustomExample> createState() => _CustomExampleState();
}

class _CustomExampleState extends State<CustomExample> {
  String receivedData = '';
  CustomViewController? _controller;

  void _onCustomAndroidViewCreated(CustomViewController controller) {
    _controller = controller;
    _controller?.customDataStream.listen((data) {
      //接收到来自Android端的数据
      setState(() {
        receivedData = '来自Android的数据：$data';
      });
    });
  }

  Widget _buildAndroidView() {
    return Expanded(
      flex: 1,
      child: Container(
        color: Colors.blueAccent.withAlpha(60),
        child: Center(child: CustomAndroidView(_onCustomAndroidViewCreated)),
      ),
    );
  }

  Widget _buildFlutterView() {
    return Expanded(
      flex: 1,
      child: Column(
        children: [
          Text(receivedData, style: const TextStyle(color: Colors.black)),
          const SizedBox(height: 10),
          SingleChildScrollView(
            child: Wrap(runSpacing: 10, spacing: 10, children: [
              TextButton(
                onPressed: () {
                  _controller?.initMimoSdk().then((value) {
                    setState(() {
                      receivedData = 'initMimoSdk：$value';
                    });
                  });
                },
                child: const Text('init'),
              ),
              TextButton(
                onPressed: () {
                  _controller?.loadBannerAd();
                },
                child: const Text('loadBannerAd'),
              ),
              TextButton(
                onPressed: () {
                  _controller?.showBannerAd();
                },
                child: const Text('showBannerAd'),
              ),
              TextButton(
                onPressed: () {
                  _controller?.loadInterstitialAd();
                },
                child: const Text('loadInterstitialAd'),
              ),
              TextButton(
                onPressed: () {
                  _controller?.showInterstitialAd();
                },
                child: const Text('showInterstitialAd'),
              ),
              TextButton(
                onPressed: () {
                  final randomNum = Random().nextInt(10);
                  _controller?.sendMessageToAndroidView('flutter - $randomNum ');
                },
                child: const Text('发送数据给Android'),
              ),
            ]),
          ),
        ],
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        _buildAndroidView(),
        _buildFlutterView(),
      ],
    );
  }
}
