package net.incongru.tichu.archunit;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import net.incongru.tichu.action.Action;
import net.incongru.tichu.simu.Simulation.PostActionCommand;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.constructors;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.members;

@AnalyzeClasses(packages = "net.incongru.tichu")
class ArchUnitTest {
    // TODO // Other interesting rules in com.tngtech.archunit.library.GeneralCodingRules

    @ArchTest
    static final ArchRule action_and_command_classes_and_ctors_should_be_package_private =
            constructors()
                    .that().areDeclaredInClassesThat().areAssignableTo(Action.class)
                    .or().areDeclaredInClassesThat().areAssignableTo(PostActionCommand.class)
                    .should().bePackagePrivate()
                    .andShould().beDeclaredInClassesThat().arePackagePrivate();

    @ArchTest
    static final ArchRule ensure_test_classes_and_method_are_not_public = // because it's just less verbose
            members()
                    .that().areAnnotatedWith(Test.class)
                    .or().areAnnotatedWith(BeforeEach.class)
                    .or().areAnnotatedWith(BeforeAll.class)
                    .or().areAnnotatedWith(AfterEach.class)
                    .or().areAnnotatedWith(AfterAll.class)
                    .or().areAnnotatedWith(ParameterizedTest.class)
                    .or().areAnnotatedWith(ArchTest.class)
                    .should().bePackagePrivate()
                    .andShould().beDeclaredInClassesThat().arePackagePrivate();
}
