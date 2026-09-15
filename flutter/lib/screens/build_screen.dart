import 'package:flutter/material.dart';

class BuildScreen extends StatelessWidget {
  const BuildScreen({super.key});
  @override Widget build(BuildContext context) => ListView(padding: const EdgeInsets.all(16), children: [
    const Text('Cloud Build', style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold)),
    const SizedBox(height: 8),
    const Text('AAA Flutter Build uses GitHub Actions to analyze Dart code and create release artifacts.'),
    const SizedBox(height: 20),
    _BuildCard(title: 'Release APK', artifact: 'aaa-flutter-apk'),
    _BuildCard(title: 'Release AAB', artifact: 'aaa-flutter-aab'),
    _BuildCard(title: 'Build both', artifact: 'APK + AAB'),
    const SizedBox(height: 12),
    const Card(child: Padding(padding: EdgeInsets.all(16), child: Text('Review before download: verify the workflow result and artifact before installing or publishing.'))),
  ]);
}

class _BuildCard extends StatelessWidget {
  final String title; final String artifact;
  const _BuildCard({required this.title, required this.artifact});
  @override Widget build(BuildContext context) => Card(margin: const EdgeInsets.only(bottom: 12), child: ListTile(leading: const Icon(Icons.cloud_upload_outlined), title: Text(title), subtitle: Text('Artifact: $artifact'), trailing: FilledButton(onPressed: () => showDialog(context: context, builder: (_) => AlertDialog(title: Text('Review $title'), content: Text('Target: $title\nArtifact: $artifact\nBuild: GitHub Actions'), actions: [TextButton(onPressed: () => Navigator.pop(context), child: const Text('Close'))])), child: const Text('Review'))));
}
