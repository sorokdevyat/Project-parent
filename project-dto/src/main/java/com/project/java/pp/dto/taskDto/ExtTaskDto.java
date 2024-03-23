package com.project.java.pp.dto.taskDto;

import com.project.java.pp.dto.ProjectDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import com.project.java.pp.dto.MemberDto;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
// Для расширеннего поиска например когда нужна только одна задача и все данные о ней
// в такое случае поля с айди дополняюттся полными данными о исполнителе, авторе и проекте
// РАсширенный дто.
public class ExtTaskDto extends BaseTaskDto {
    private MemberDto executor;
    private MemberDto author;
    private ProjectDto project;
}
