import 'package:flutter/material.dart';
import 'screens/home_screen.dart';

void main() {
  runApp(const AAAMobileStudio());
}

class AAAMobileStudio extends StatelessWidget {
  const AAAMobileStudio({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'AAA Mobile Studio',
      debugShowCheckedModeBanner: false,
      theme: ThemeData.dark(useMaterial3: true).copyWith(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.blue, brightness: Brightness.dark),
        scaffoldBackgroundColor: const Color(0xFF0B0D12),
      ),
      home: const HomeScreen(),
    );
  }
}
