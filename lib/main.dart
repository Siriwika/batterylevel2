import 'package:flutter/material.dart';
import 'dart:async';
import 'package:flutter/services.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({Key? key, required this.title}) : super(key: key);

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class User{
  User({
    required  this.name,
    required this.age
});
  String name;
  int age;

}

class _MyHomePageState extends State<MyHomePage> {
  static const platform = MethodChannel('samples.flutter.dev/battery');

// Get battery level.
  late User _user =  User(name: "", age:0);

  Future<void> _getBatteryLevel() async {

    try {
      final result = await platform.invokeMethod('getBatteryLevel');
      _user.name = result['name'];
      // print(result);
      // print(result['age']);
      // print(result.runtimeType);

    } on PlatformException catch (e) {
      _user.name = "Failed to get battery level: '${e.message}'.";
    }

    setState(() {
      _user = _user;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Material(
      child: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          children: [
            ElevatedButton(
              onPressed: _getBatteryLevel,
              child: const Text('Get Name'),
            ),
            Text(_user.name.toString()),
          ],
        ),
      ),
    );
  }
}
