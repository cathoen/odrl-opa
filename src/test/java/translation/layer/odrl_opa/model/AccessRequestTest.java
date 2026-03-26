package translation.layer.odrl_opa.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AccessRequestTest {

    @Test
    void getterSetter_shouldWorkCorrectly() {
        AccessRequest request = new AccessRequest();

        // set values
        request.setRole("teacher");
        request.setAction("read");
        request.setResource("student_grade");

        // assert getters
        assertThat(request.getRole()).isEqualTo("teacher");
        assertThat(request.getAction()).isEqualTo("read");
        assertThat(request.getResource()).isEqualTo("student_grade");
    }
}