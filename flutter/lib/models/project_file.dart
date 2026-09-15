class ProjectFile {
  final String path;
  final String content;
  const ProjectFile({required this.path, this.content = ''});

  String get extension => path.contains('.') ? path.split('.').last : '';
  bool get isDart => extension == 'dart';
  bool get isAsset => path.startsWith('assets/');
}
