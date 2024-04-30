package com.finale;

import com.finale.entity.CoachRole;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EnumTest {

    @Test
    void valueOfEnum() throws Exception {
        //given
        String master = "MASTER";

        //when
        CoachRole coachRole = CoachRole.valueOf(master);

        //then
        assertThat(coachRole).isEqualTo(CoachRole.MASTER);
    }

    @Test
    void getEnumByString() throws Exception {
        //given
        String master = "master";

        //when
        CoachRole coachRole = CoachRole.getEnum(master);

        //then
        assertThat(coachRole).isEqualTo(CoachRole.MASTER);
    }
}
