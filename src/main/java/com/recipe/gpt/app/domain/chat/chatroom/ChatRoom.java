package com.recipe.gpt.app.domain.chat.chatroom;

import com.recipe.gpt.app.domain.member.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    private ChatRoom(String name, Member member) {
        this.name = name;
        this.member = member;
    }

    public boolean isNotPossibleToAccessPlaylist(Member member) {
        if (member == null) {
            return true;
        }

        Long loginMemberId = member.getId();
        Long memberId = this.member.getId();
        return !memberId.equals(loginMemberId);
    }

    public void update(ChatRoom requestChatRoom) {
        this.name = requestChatRoom.name;
    }

}
