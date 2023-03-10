package com.alzzaipo.web.domain.Portfolio;

import com.alzzaipo.web.domain.BaseTimeEntity;
import com.alzzaipo.web.domain.IPO.IPO;
import com.alzzaipo.web.domain.Member.Member;
import com.alzzaipo.web.dto.PortfolioListDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Getter
@NoArgsConstructor
@Entity
public class Portfolio extends BaseTimeEntity {
    @Id @GeneratedValue
    @Column(name = "portfolio_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ipo_id")
    private IPO ipo;

    private int sharesCnt;

    private int profit;

    private int profitRate;

    private String agents;

    @Column(columnDefinition = "TEXT")
    private String memo;

    @Builder
    public Portfolio(Member member, IPO ipo, int sharesCnt, int profit, String agents, String memo) {
        this.member = member;
        this.ipo = ipo;
        this.sharesCnt = sharesCnt;
        this.profit = profit;
        this.agents = agents;
        this.memo = memo;
        this.profitRate = calcProfitRate();
    }

    public void changeAllocatedSharesCnt(int sharesCnt) {
        this.sharesCnt = sharesCnt;
    }

    public int changeProfit(int profit) {
        this.profit = profit;
        this.profitRate = calcProfitRate();
        return profit;
    }

    private int calcProfitRate() {
        if(this.sharesCnt != 0) {
            int profitPerShare = this.profit / this.sharesCnt;
            int fixedOfferingPrice = this.ipo.getFixedOfferingPrice();
            return (profitPerShare * 100) / fixedOfferingPrice;
        }

        return 0;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void changeId(Long id) { this.id = id; }

    @Transactional(readOnly = true)
    public PortfolioListDto toDto() {
        PortfolioListDto portfolioListDto = PortfolioListDto.builder()
                .portfolioId(this.id)
                .stockName(this.ipo.getStockName())
                .stockCode(this.ipo.getStockCode())
                .subscribeStartDate(this.ipo.getSubscribeStartDate())
                .subscribeEndDate(this.ipo.getSubscribeEndDate())
                .listedDate(this.ipo.getListedDate())
                .fixedOfferingPrice(this.ipo.getFixedOfferingPrice())
                .agents(this.agents)
                .sharesCnt(this.sharesCnt)
                .profit(this.profit)
                .profitRate(this.profitRate)
                .memo(this.memo)
                .build();

        return portfolioListDto;
    }
}
