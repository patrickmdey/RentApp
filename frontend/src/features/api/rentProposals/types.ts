export type RentProposalState = 'PENDING' | 'ACCEPTED' | 'DECLINED';

export interface RentProposal {
	message: string;
	startDate: string;
	endDate: string;
	seen: boolean;
	state: RentProposalState;
	url: string;
	articleUrl: string;
	renterUrl: string;
	id: number;
}

export interface ListRentProposalsParameters {
	userId: number; // TODO: esto tambien seria parte del token
	page?: number;
	type?: string;
	state?: string;
}

export interface CreateRentProposalParameters {
	message: string;
	startDate: string;
	endDate: string;
	articleId: number;
	renterId: number | null; // TODO: esto seria el token de auth en realidad
}

export interface UpdateRentProposalParameters {
	state: string;
	url: string;
}
