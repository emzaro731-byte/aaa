import 'package:flutter/material.dart';

class AIBuilderScreen extends StatefulWidget {
  const AIBuilderScreen({super.key});
  @override State<AIBuilderScreen> createState() => _AIBuilderScreenState();
}

class _AIBuilderScreenState extends State<AIBuilderScreen> {
  final prompt = TextEditingController();
  String result = '';
  @override void dispose() { prompt.dispose(); super.dispose(); }

  @override Widget build(BuildContext context) => ListView(padding: const EdgeInsets.all(16), children: [
    const Text('AI Flutter Builder', style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold)),
    const SizedBox(height: 8),
    const Text('Describe the Flutter app you want. This screen prepares a structured generation request for a secure backend AI service.'),
    const SizedBox(height: 16),
    TextField(controller: prompt, minLines: 5, maxLines: 10, decoration: const InputDecoration(border: OutlineInputBorder(), hintText: 'Build a marketplace app with phone login, product search and checkout...')),
    const SizedBox(height: 12),
    FilledButton.icon(onPressed: prompt.text.trim().isEmpty ? null : () => setState(() => result = 'Generation request prepared for: ${prompt.text.trim()}'), icon: const Icon(Icons.auto_awesome), label: const Text('Generate Flutter Project')),
    if (result.isNotEmpty) Padding(padding: const EdgeInsets.only(top: 16), child: Card(child: Padding(padding: const EdgeInsets.all(16), child: Text(result)))),
    const SizedBox(height: 12),
    const Text('Security: keep Groq, KIE, Supabase service-role and GitHub credentials in a backend or GitHub Secrets, never in the APK.', style: TextStyle(color: Colors.white60)),
  ]);
}
