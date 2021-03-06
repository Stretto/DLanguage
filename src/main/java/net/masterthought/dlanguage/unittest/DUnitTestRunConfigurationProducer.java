package net.masterthought.dlanguage.unittest;

import com.intellij.execution.actions.ConfigurationContext;
import com.intellij.execution.actions.RunConfigurationProducer;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.util.Ref;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.DLanguageWritingAccessProvider;
import net.masterthought.dlanguage.psi.DLanguageClassDeclaration;
import net.masterthought.dlanguage.psi.DLanguageFile;
import net.masterthought.dlanguage.psi.DLanguageIdentifier;
import net.masterthought.dlanguage.psi.DLanguageTemplateMixin;
import net.masterthought.dlanguage.utils.DUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

import static net.masterthought.dlanguage.utils.DUtil.*;

public class DUnitTestRunConfigurationProducer extends RunConfigurationProducer<DUnitTestRunConfiguration> {


    public DUnitTestRunConfigurationProducer() {
        super(DUnitTestRunConfigurationType.getInstance());
    }

    @Override
    protected boolean setupConfigurationFromContext(DUnitTestRunConfiguration configuration, ConfigurationContext context, Ref<PsiElement> sourceElement) {
        final VirtualFile dFile = getRunnableDFileFromContext(context);
        if (dFile != null) {
            configuration.setdFilePath(dFile.getPath());
            configuration.setWorkingDir(dFile.getParent().getPath());
            configuration.setGeneratedName();

            sourceElement.set(sourceElement.isNull() ? null : sourceElement.get().getContainingFile());
            return true;
        }
        return false;
    }

    @Nullable
    public static VirtualFile getRunnableDFileFromContext(final @NotNull ConfigurationContext context) {
        final PsiElement psiLocation = context.getPsiLocation();
        final PsiFile psiFile = psiLocation == null ? null : psiLocation.getContainingFile();
        final VirtualFile virtualFile = getRealVirtualFile(psiFile);

        if ((psiFile instanceof DLanguageFile) &&
                virtualFile != null &&
                ProjectRootManager.getInstance(context.getProject()).getFileIndex().isInContent(virtualFile) &&
                !DLanguageWritingAccessProvider.isInDLanguageSdkOrDLanguagePackagesFolder(psiFile.getProject(), virtualFile)) {

            // only run this producer if is test file
            if (isDunitTestFile(psiFile)) {
                return virtualFile;
            } else {
                return null;
            }
        }

        return null;
    }

    @Override
    public boolean isConfigurationFromContext(final @NotNull DUnitTestRunConfiguration configuration,
                                              final @NotNull ConfigurationContext context) {
        final VirtualFile dartFile = getDFileFromContext(context);
        return dartFile != null && dartFile.getPath().equals(configuration.getdFilePath());
    }

    @Nullable
    private static VirtualFile getDFileFromContext(final @NotNull ConfigurationContext context) {
        final PsiElement psiLocation = context.getPsiLocation();
        final PsiFile psiFile = psiLocation == null ? null : psiLocation.getContainingFile();
        final VirtualFile virtualFile = getRealVirtualFile(psiFile);
        return psiFile instanceof DLanguageFile && virtualFile != null ? virtualFile : null;
    }

    public static VirtualFile getRealVirtualFile(PsiFile psiFile) {
        return psiFile != null ? psiFile.getOriginalFile().getVirtualFile() : null;
    }


}