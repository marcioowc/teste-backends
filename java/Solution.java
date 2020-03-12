import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class Solution {
	
	/*
	 * There isn't "the best way" to do this job, but using static methods limits the development. I could use CDI, EJB, 
	 * design patterns (MVC, MVP, MVVM, etc) and others if I could used a maven or web project. I didn't use request and response in Lists for reuse 
	 * them, because the payload isn't "heavy".
	 * 
	 * Architecture:
	 * 1 - Read the file and create List by Schema and Action to split the code;
	 * 2 - Remove deleted events and their children;
	 * 3 - Organize the timestamp, updates and priorities;
	 * 4 - Validate section.
	 * 
	*/
	public static String processMessages(List<String> messages) {
		
		List<String> proposalsValids = new ArrayList<String>(); 
		List<Proposal> proposals = new ArrayList<Proposal>();
		List<Proponent> proponents = new ArrayList<Proponent>();
		List<Warranty> warranties = new ArrayList<Warranty>();
		List<Proposal> proposalsR = new ArrayList<Proposal>();
		List<Proponent> proponentsR = new ArrayList<Proponent>();
		List<Warranty> warrantiesR = new ArrayList<Warranty>();
		
		/*
		 * CREATETING LISTS BY SCHEMA AND ACTION
		*/

		for (int i = 0; i < messages.size(); i++) {
			String[] line = messages.get(i).split(",");
			
			if (line[1].equals("proposal")) {
				switch(line[2]) {
					case "created":
						proposals.add(new Proposal(line[0], line[1], line[2], line[3], line[4], line[5], line[6]));
						break;
					case "updated":
						proposals.add(new Proposal(line[0], line[1], line[2], line[3], line[4], line[5], line[6]));
						break;
					case "deleted":
						proposalsR.add(new Proposal(line[0], line[1], line[2], line[3], line[4], null, null));
						break;
				}
			} else if (line[1].equals("proponent")) {
				
				switch(line[2]) {
					case "added":
						proponents.add(new Proponent(line[0], line[1], line[2], line[3], line[4], line[5], line[6], line[7],
								line[8], line[9]));
						break;
					case "updated":
						proponents.add(new Proponent(line[0], line[1], line[2], line[3], line[4], line[5], line[6], line[7],
								line[8], line[9]));
						break;
					case "removed":
						proponentsR.add(new Proponent(line[0], line[1], line[2], line[3], line[4], line[5], null, null,
								null, null));
						break;
				}

			} else if (line[1].equals("warranty")){
				
				switch(line[2]) {
					case "added":
						warranties.add(new Warranty(line[0], line[1], line[2], line[3], line[4], line[5], line[6], line[7]));
						break;
					case "updated":
						warranties.add(new Warranty(line[0], line[1], line[2], line[3], line[4], line[5], line[6], line[7]));
						break;
					case "removed":
						warrantiesR.add(new Warranty(line[0], line[1], line[2], line[3], line[4], line[5], null, null));
						break;
				}
			}
		}
		
		/*
		 * REMOVE DELETED EVENTS
		*/
		proposals.removeAll(proplRemoving(proposals, proposalsR));
		proponents.removeAll(proptRemoving(proponents, proponentsR));
		warranties.removeAll(wrntRemoving(warranties, warrantiesR));
		
		/*
		 * FIX THE TIMESTAMP UPDATES AND PRIORITIES
		*/			
		proposals = proplUpdating(proposals).stream()
				.distinct().collect(Collectors.toList());
		
		proponents = proptUpdating(proponents).stream()
				.distinct().collect(Collectors.toList());
		
		warranties = wrntUpdating(warranties).stream()
				.distinct().collect(Collectors.toList());
		
		/*
		 * VALIDATIONS 
		*/
		for (Proposal proposal : proposals) {
			if(isValidValue(proposal)) {
				if(isValidPaymentTerm(proposal)) {
					if (hasMainProponent(proposal, proponents)) {
						if (proponentAmount(proposal, proponents) >= 2) {
							if(validAge(proposal, proponents)) {
								if(warrantyAmount(proposal, warranties) > 0) {
									if(warrantiesValueAmount(proposal, warranties) >= (Double.parseDouble(proposal.getProposalLoanValue())) * 2) {
										if(validState(proposal, warranties)) {
											if(validMainProponentMonthlyIncome(proposal, proponents)) {
												proposalsValids.add(proposal.getProposalId());
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		return proposalsValids.stream()               
                .collect(Collectors.joining(","));
	}
	
	private static List<Proposal> proplRemoving(List<Proposal> proposals, List<Proposal> proposalsR){
		List<Proposal> proplRemoving = new ArrayList<Proposal>();
		for(Proposal proposal : proposalsR) {
			proplRemoving = proposals.stream()
					.filter((p)->p.getProposalId().equals(proposal.getProposalId()))
					.collect(Collectors.toList());
		}
		return proplRemoving;
	}
	
	private static List<Proponent> proptRemoving(List<Proponent> proponents, List<Proponent> proponentsR){
		List<Proponent> proptRemoving = new ArrayList<Proponent>();
		for(Proponent proponent : proponentsR) {
			proptRemoving = proponents.stream()
					.filter((p)->p.getProposalId().equals(proponent.getProposalId()))
					.collect(Collectors.toList());
		}
		return proptRemoving;
	}
	
	private static List<Warranty> wrntRemoving(List<Warranty> warranties, List<Warranty> warrantiesR) {
		List<Warranty> wrntRemoving = new ArrayList<Warranty>();
		for(Warranty warranty : warrantiesR) {
			wrntRemoving = warranties.stream()
					.filter((w)->w.getProposalId().equals(warranty.getProposalId()))
					.collect(Collectors.toList());
		}
		return wrntRemoving;
	}
	
	private static List<Proposal> proplUpdating(List<Proposal> proposals){
		List<Proposal> proplUpdating = new ArrayList<Proposal>();
		for(Proposal proposal : proposals) {
			Proposal proposalU = proposals.stream()
					.filter((p)-> p.getProposalId().equals(proposal.getProposalId()))
					.max(Comparator.comparing(Proposal::dateToInt))
					.orElseThrow(NoSuchElementException::new);
			proplUpdating.add(proposalU);
		}
		return proplUpdating;
	}
	
	private static List<Proponent> proptUpdating(List<Proponent> proponents){
		List<Proponent> proptUpdating = new ArrayList<Proponent>();
		for(Proponent proponent : proponents) {
			Proponent proponentU = proponents.stream()
					.filter((p)-> p.getProposalId().equals(proponent.getProposalId()))
					.filter((p)-> p.getProponentId().equals(proponent.getProponentId()))
					.max(Comparator.comparing(Proponent::dateToInt))
					.orElseThrow(NoSuchElementException::new);
			proptUpdating.add(proponentU);
			
		}
		return proptUpdating;
	}
	
	private static List<Warranty> wrntUpdating(List<Warranty> warranties) {
		List<Warranty> wrntUpdating = new ArrayList<Warranty>();
		for(Warranty warranty : warranties) {
			Warranty warrantyU = warranties.stream()
					.filter((w)-> w.getProposalId().equals(warranty.getProposalId()))
					.filter((w)-> w.getWarrantyId().equals(warranty.getWarrantyId()))
					.max(Comparator.comparing(Warranty::dateToInt))
					.orElseThrow(NoSuchElementException::new);
			wrntUpdating.add(warrantyU);
		}
		return wrntUpdating;
	}
	
	private static boolean isValidValue(Proposal proposal) {
		boolean pReturn = false;
		if (Double.parseDouble(proposal.getProposalLoanValue()) >= 30000
				&& Double.parseDouble(proposal.getProposalLoanValue()) <= 3000000) {
			pReturn = true;
		}
		return pReturn;
	}
	
	private static boolean isValidPaymentTerm(Proposal proposal) {
		boolean pReturn = false;
		if (Integer.parseInt(proposal.getMonths()) >= 24 && Integer.parseInt(proposal.getMonths()) <= 180) {
			pReturn = true;
		}
		return pReturn;
	}

	private static Long proponentAmount(Proposal proposal, List<Proponent> proponents) {
		return proponents.stream()
				.filter((p) -> p.getProposalId().equals(proposal.getProposalId()))
				.count();
	}

	private static boolean hasMainProponent(Proposal proposal, List<Proponent> proponents) {
		Proponent proponent = proponents.stream()
				.filter((p) -> p.getProposalId().equals(proposal.getProposalId()))
				.filter((p) -> p.getProponentMain().equals("true"))
				.findFirst()
				.orElse(null);
		return proponent != null ? true : false;
	}
	
	private static boolean validAge(Proposal proposal, List<Proponent> proponents) {
		Proponent proponent = proponents.stream()
				.filter((p) -> p.getProposalId().equals(proposal.getProposalId()))
				.filter((p) -> Integer.parseInt(p.getProponentAge()) >= 18)
				.findFirst()
				.orElse(null);
		return proponent != null ? true : false;
	}
	
	private static Long warrantyAmount(Proposal proposal, List<Warranty> warranties) {
		return warranties.stream()
				.filter((w) -> w.getProposalId().equals(proposal.getProposalId()))
				.count();
	}
	
	private static Double warrantiesValueAmount(Proposal proposal, List<Warranty> warranties) {
		Double amount = new Double(0);
		List<Warranty> warrantiesP = warranties.stream()
				.filter((w) -> w.getProposalId().equals(proposal.getProposalId()))
				.collect(Collectors.toList());
		for(Warranty warranty : warrantiesP) {
			amount += Double.parseDouble(warranty.getWarrantyValue());
		}
		return amount;
	}
	
	private static boolean validState(Proposal proposal, List<Warranty> warranties){
		boolean pReturn = false;
		List<Warranty> warrantiesP = warranties.stream()
				.filter((w) -> w.getProposalId().equals(proposal.getProposalId()))
				.collect(Collectors.toList());
		for(Warranty warranty : warrantiesP) {
			pReturn = true;
			if(warranty.getWarrantyProvince().equals("PR") || warranty.getWarrantyProvince().equals("SC") || warranty.getWarrantyProvince().equals("RS")) {
				pReturn = false;
				break;
			}
		}
		return pReturn;
	}
	
	private static boolean validMainProponentMonthlyIncome(Proposal proposal, List<Proponent> proponents) {
		boolean pReturn = false;
		List<Proponent> proponentsP = proponents.stream()
				.filter((p) -> p.getProposalId().equals(proposal.getProposalId()))
				.collect(Collectors.toList());
		for(Proponent p : proponentsP) {
			pReturn = false;
			if(Integer.parseInt(p.getProponentAge()) >= 18 && Integer.parseInt(p.getProponentAge()) < 24 && (Double.parseDouble(p.getProponentMonthlyIncome())) >= ((Double.parseDouble(proposal.getProposalLoanValue()) / Integer.parseInt(proposal.getMonths())) * 4)){
				pReturn = true;
			}else if(Integer.parseInt(p.getProponentAge()) >= 24 && Integer.parseInt(p.getProponentAge()) < 50 && (Double.parseDouble(p.getProponentMonthlyIncome())) >= ((Double.parseDouble(proposal.getProposalLoanValue()) / Integer.parseInt(proposal.getMonths())) * 3)){
				pReturn = true;
			}else if(Integer.parseInt(p.getProponentAge()) >= 50 && (Double.parseDouble(p.getProponentMonthlyIncome())) >= ((Double.parseDouble(proposal.getProposalLoanValue()) / Integer.parseInt(proposal.getMonths())) * 2)){
				pReturn = true;
			}
			if(!pReturn) {
				break;
			}
		}
		return pReturn;
	}
}
