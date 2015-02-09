package actuseries.android.com.actuseries.event;

import actuseries.android.com.actuseries.metier.Member;

/**
 * Created by charly on 14/01/2015.
 */
public class LoginResultEvent {
    private Member member;

    public LoginResultEvent(Member member) {
        this.member = member;
    }

    public Member getResult() {
        return this.member;
    }
}
