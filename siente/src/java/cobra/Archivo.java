package cobra;

public class Archivo {
    private String name;
    private String onlyName;
    private String mime;
    private long length;
    private byte[] data;

    public byte[] getData() {
        return data;
    }

    public void setOnlyName(String onlyName) {
        this.onlyName = onlyName;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getMime() {
        if (name != null && !name.equals("")) {

            int indexmime = name.lastIndexOf('.');
            if (indexmime >= 0) {
                mime = name.substring(indexmime + 1);
            } else {
               mime=null;
            }
        }
        
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public String getName() {
        return name;
    }

    public String getOnlyName() {
        if (name != null && !name.equals("")) {

            int indexNombreArchivo = name.lastIndexOf('/');
            if (indexNombreArchivo >= 0) {
                onlyName = name.substring(indexNombreArchivo + 1);
            } else {
                // Try backslash
                indexNombreArchivo = name.lastIndexOf('\\');
                if (indexNombreArchivo >= 0) {
                    onlyName = name.substring(indexNombreArchivo + 1);
                } else {
                    // No forward or back slashes
                    onlyName = name;
                }
            }
        }
        return onlyName;
    }

    public void setName(String ubicacionArchivoLocal) {

        this.name = ubicacionArchivoLocal;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }
}
