package de.simbuildings.tilemapper.ui.common;

import de.simbuildings.tilemapper.common.Exportable;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;

@Singleton
public class ExportModel {
    private ExportJob currentExport;

    @Inject
    public ExportModel() {
        // constructor for injection
    }

    public Optional<ExportJob> getCurrentExportJob() {
        return Optional.ofNullable(currentExport);
    }

    public void setCurrentExportJob(ExportJob currentExport) {
        this.currentExport = currentExport;
    }

    public static final class ExportJob {   // record cannot compile. Can be converted to record in the future.
        private final Exportable exportable;
        private final Path output;

        public ExportJob(Exportable exportable, Path output) {
            this.exportable = exportable;
            this.output = output;
        }

        public Exportable exportable() {
            return exportable;
        }

        public Path output() {
            return output;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (ExportJob) obj;
            return Objects.equals(this.exportable, that.exportable) &&
                    Objects.equals(this.output, that.output);
        }

        @Override
        public int hashCode() {
            return Objects.hash(exportable, output);
        }

        @Override
        public String toString() {
            return "ExportJob[" +
                    "exportable=" + exportable + ", " +
                    "output=" + output + ']';
        }
    }
}
