import '../models/project_file.dart';

class ProjectStore {
  final Map<String, ProjectFile> _files = {
    'lib/main.dart': const ProjectFile(path: 'lib/main.dart'),
  };

  List<ProjectFile> get files => _files.values.toList()..sort((a, b) => a.path.compareTo(b.path));

  void put(String path, String content) {
    final clean = path.trim();
    if (clean.isEmpty) return;
    _files[clean] = ProjectFile(path: clean, content: content);
  }

  ProjectFile? get(String path) => _files[path];
  void remove(String path) => _files.remove(path);
}
