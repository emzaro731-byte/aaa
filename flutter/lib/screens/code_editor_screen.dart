import 'package:flutter/material.dart';

class CodeEditorScreen extends StatefulWidget {
  const CodeEditorScreen({super.key});

  @override
  State<CodeEditorScreen> createState() => _CodeEditorScreenState();
}

class _CodeEditorScreenState extends State<CodeEditorScreen> {
  final controller = TextEditingController(text: '''import 'package:flutter/material.dart';

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      home: Scaffold(
        body: Center(child: Text('Hello from AAA')),
      ),
    );
  }
}''');

  @override
  void dispose() {
    controller.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(12),
      child: Column(
        children: [
          Row(children: [
            const Expanded(child: Text('Dart / Flutter Editor', style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold))),
            FilledButton.icon(onPressed: () => ScaffoldMessenger.of(context).showSnackBar(const SnackBar(content: Text('Flutter code ready for cloud build.'))), icon: const Icon(Icons.save), label: const Text('Save')),
          ]),
          const SizedBox(height: 10),
          Expanded(
            child: TextField(
              controller: controller,
              expands: true,
              maxLines: null,
              minLines: null,
              style: const TextStyle(fontFamily: 'monospace', fontSize: 13),
              decoration: const InputDecoration(border: OutlineInputBorder(), hintText: 'Write Dart / Flutter code...'),
            ),
          ),
        ],
      ),
    );
  }
}
