import 'package:flutter/material.dart';

class ProjectsScreen extends StatefulWidget {
  const ProjectsScreen({super.key});
  @override State<ProjectsScreen> createState() => _ProjectsScreenState();
}

class _ProjectsScreenState extends State<ProjectsScreen> {
  final files = <String>{
    'lib/main.dart','lib/screens/home_screen.dart','lib/screens/code_editor_screen.dart','lib/screens/projects_screen.dart','lib/screens/ai_builder_screen.dart','lib/screens/build_screen.dart','lib/services/project_store.dart','lib/models/project_file.dart','assets/images/.gitkeep','test/widget_test.dart','pubspec.yaml','analysis_options.yaml','README.md'
  };
  String filter = '';

  @override Widget build(BuildContext context) {
    final visible = files.where((f) => filter.isEmpty || f.toLowerCase().contains(filter.toLowerCase())).toList()..sort();
    return Column(children: [
      Padding(padding: const EdgeInsets.all(12), child: Row(children: [const Expanded(child: Text('Flutter Project Explorer', style: TextStyle(fontSize: 22, fontWeight: FontWeight.bold))), FilledButton.icon(onPressed: () => _newFile(), icon: const Icon(Icons.add), label: const Text('New'))])),
      Padding(padding: const EdgeInsets.symmetric(horizontal: 12), child: TextField(onChanged: (v) => setState(() => filter = v), decoration: const InputDecoration(prefixIcon: Icon(Icons.search), hintText: 'Search files', border: OutlineInputBorder()))),
      const SizedBox(height: 8),
      Expanded(child: ListView(children: visible.map((file) => Card(child: ListTile(leading: Icon(_icon(file)), title: Text(file), subtitle: Text(_type(file)), trailing: IconButton(icon: const Icon(Icons.more_vert), onPressed: () => _showActions(file)))).toList())),
    ]);
  }

  IconData _icon(String p) => p.endsWith('.dart') ? Icons.code : p.endsWith('.yaml') ? Icons.settings : p.startsWith('assets/') ? Icons.image_outlined : Icons.insert_drive_file_outlined;
  String _type(String p) => p.endsWith('.dart') ? 'Dart / Flutter' : p.endsWith('.yaml') ? 'Configuration' : p.startsWith('assets/') ? 'Asset' : 'Project file';

  void _newFile() { final c = TextEditingController(); showDialog(context: context, builder: (_) => AlertDialog(title: const Text('Create Flutter file'), content: TextField(controller: c, decoration: const InputDecoration(hintText: 'lib/services/api_service.dart')), actions: [TextButton(onPressed: () => Navigator.pop(context), child: const Text('Cancel')), FilledButton(onPressed: () { if (c.text.trim().isNotEmpty) setState(() => files.add(c.text.trim())); Navigator.pop(context); }, child: const Text('Create'))])); }
  void _showActions(String file) => showModalBottomSheet(context: context, builder: (_) => SafeArea(child: Wrap(children: [ListTile(leading: const Icon(Icons.edit), title: const Text('Open in editor'), onTap: () => Navigator.pop(context)), ListTile(leading: const Icon(Icons.delete_outline), title: const Text('Remove from workspace'), onTap: () { setState(() => files.remove(file)); Navigator.pop(context); })])));
}
