import 'package:flutter/material.dart';
import 'code_editor_screen.dart';
import 'projects_screen.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({super.key});

  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  int index = 0;

  final pages = const [
    _Dashboard(),
    ProjectsScreen(),
    CodeEditorScreen(),
    _BuildPage(),
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('AAA Mobile Studio')),
      body: pages[index],
      bottomNavigationBar: NavigationBar(
        selectedIndex: index,
        onDestinationSelected: (value) => setState(() => index = value),
        destinations: const [
          NavigationDestination(icon: Icon(Icons.dashboard_outlined), label: 'Home'),
          NavigationDestination(icon: Icon(Icons.folder_outlined), label: 'Projects'),
          NavigationDestination(icon: Icon(Icons.code), label: 'Dart'),
          NavigationDestination(icon: Icon(Icons.build_outlined), label: 'Build'),
        ],
      ),
    );
  }
}

class _Dashboard extends StatelessWidget {
  const _Dashboard();

  @override
  Widget build(BuildContext context) {
    return ListView(
      padding: const EdgeInsets.all(20),
      children: const [
        Text('Build apps from your phone', style: TextStyle(fontSize: 25, fontWeight: FontWeight.bold)),
        SizedBox(height: 8),
        Text('Flutter-first workspace with Dart editing, projects and cloud builds.'),
        SizedBox(height: 20),
        _Feature(title: 'Flutter + Dart', body: 'Write Flutter code directly in the mobile workspace.'),
        _Feature(title: 'Project Workspace', body: 'Manage Flutter source files and project folders.'),
        _Feature(title: 'APK / AAB', body: 'Build Flutter Android packages with GitHub Actions.'),
        _Feature(title: 'AI Ready', body: 'Add a secure backend AI provider without putting private keys in the APK.'),
      ],
    );
  }
}

class _Feature extends StatelessWidget {
  final String title;
  final String body;
  const _Feature({required this.title, required this.body});

  @override
  Widget build(BuildContext context) => Card(
        margin: const EdgeInsets.only(bottom: 12),
        child: ListTile(
          leading: const Icon(Icons.auto_awesome),
          title: Text(title, style: const TextStyle(fontWeight: FontWeight.bold)),
          subtitle: Text(body),
        ),
      );
}

class _BuildPage extends StatelessWidget {
  const _BuildPage();

  @override
  Widget build(BuildContext context) => Center(
        child: FilledButton.icon(
          onPressed: () => showDialog<void>(
            context: context,
            builder: (_) => const AlertDialog(
              title: Text('Cloud Build'),
              content: Text('Use the GitHub Actions Flutter workflow to build the APK and AAB.'),
            ),
          ),
          icon: const Icon(Icons.cloud_upload),
          label: const Text('Build Flutter App'),
        ),
      );
}
