import 'package:flutter/material.dart';

class ProjectsScreen extends StatelessWidget {
  const ProjectsScreen({super.key});

  @override
  Widget build(BuildContext context) {
    final files = const [
      'lib/main.dart',
      'lib/screens/home_screen.dart',
      'lib/screens/code_editor_screen.dart',
      'lib/screens/projects_screen.dart',
      'pubspec.yaml',
      'README.md',
    ];
    return ListView(
      padding: const EdgeInsets.all(16),
      children: [
        const Text('Flutter Project', style: TextStyle(fontSize: 22, fontWeight: FontWeight.bold)),
        const SizedBox(height: 8),
        const Text('aaa_mobile_studio'),
        const SizedBox(height: 16),
        ...files.map((file) => Card(child: ListTile(leading: const Icon(Icons.insert_drive_file_outlined), title: Text(file)))),
        const SizedBox(height: 12),
        OutlinedButton.icon(
          onPressed: () => ScaffoldMessenger.of(context).showSnackBar(const SnackBar(content: Text('New Flutter file action is ready for the project workspace.'))),
          icon: const Icon(Icons.add),
          label: const Text('Add Flutter file'),
        ),
      ],
    );
  }
}
