import 'package:flutter/material.dart';
import 'code_editor_screen.dart';
import 'projects_screen.dart';
import 'ai_builder_screen.dart';
import 'build_screen.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({super.key});
  @override State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  int index = 0;
  final pages = const [_Dashboard(), ProjectsScreen(), CodeEditorScreen(), AIBuilderScreen(), BuildScreen()];
  final labels = const ['Home','Projects','Dart','AI Builder','Build'];
  final icons = const [Icons.dashboard_outlined, Icons.folder_outlined, Icons.code, Icons.auto_awesome, Icons.build_outlined];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('AAA Mobile Studio'), actions: [IconButton(onPressed: () => showAboutDialog(context: context, applicationName: 'AAA Mobile Studio', applicationVersion: '2.0 Flutter'), icon: const Icon(Icons.info_outline))]),
      body: pages[index],
      bottomNavigationBar: NavigationBar(selectedIndex: index, onDestinationSelected: (v) => setState(() => index = v), destinations: List.generate(labels.length, (i) => NavigationDestination(icon: Icon(icons[i]), label: labels[i]))),
    );
  }
}

class _Dashboard extends StatelessWidget {
  const _Dashboard();
  @override
  Widget build(BuildContext context) => ListView(padding: const EdgeInsets.all(20), children: const [
    Text('Build apps from your phone', style: TextStyle(fontSize: 26, fontWeight: FontWeight.bold)),
    SizedBox(height: 8), Text('A Flutter-first mobile development workspace with Dart editing, project management, AI generation and cloud APK/AAB builds.'),
    SizedBox(height: 20),
    _Feature(icon: Icons.code, title: 'Flutter + Dart', body: 'Edit Flutter code directly in the mobile workspace.'),
    _Feature(icon: Icons.account_tree_outlined, title: 'Project Tree', body: 'Work with lib, screens, services, models, assets and configuration files.'),
    _Feature(icon: Icons.auto_awesome, title: 'AI Builder', body: 'Describe an app and generate a structured Flutter starter project.'),
    _Feature(icon: Icons.cloud_upload_outlined, title: 'Cloud Build', body: 'Use GitHub Actions to produce release APK and AAB artifacts.'),
  ]);
}

class _Feature extends StatelessWidget {
  final IconData icon; final String title; final String body;
  const _Feature({required this.icon, required this.title, required this.body});
  @override Widget build(BuildContext context) => Card(margin: const EdgeInsets.only(bottom: 12), child: ListTile(leading: Icon(icon), title: Text(title, style: const TextStyle(fontWeight: FontWeight.bold)), subtitle: Text(body)));
}
