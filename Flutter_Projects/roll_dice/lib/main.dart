import 'package:flutter/material.dart';
import 'gradient_container.dart';

void main() {
  runApp(
    MaterialApp(
      home: Scaffold(
        body: GradientContainer(
          Color.fromARGB(255, 28, 16, 159),
          Color.fromARGB(255, 44, 209, 162),
        ),
      ),
    ),
  );
}
