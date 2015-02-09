package actuseries.android.com.actuseries.event;

import actuseries.android.com.actuseries.metier.Member;

/**
 * Created by Thomas on 09/02/2015.
 */
public class GetMemberSummaryResultEvent {
    private Member member;

    public GetMemberSummaryResultEvent(Member member) {
        this.member = member;
    }

    public Member getResult() {
        return this.member;
    }
}
