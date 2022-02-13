export type RentProposalState = 'PENDING' | 'ACCEPTED' | 'DECLINED';

export interface RentProposal {
	message: string;
	startDate: string;
	endDate: string;
	marked: boolean;
	state: RentProposalState;
	url: string;
	articleUrl: string;
	renterUrl: string;
	id: number;
}

export interface ListRentProposalsParameters {
	userId: number;
	page?: number;
	type?: string;
	state?: string;
}

export interface CreateRentProposalParameters {
	message: string;
	startDate: string;
	endDate: string;
	articleId: number;
	renterId: number | null;
}

export interface UpdateRentProposalParameters {
	state: string;
	url: string;
}
