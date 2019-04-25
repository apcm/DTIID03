package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.ActorRepository;
import repositories.SocialProfileRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.SocialProfile;

@Transactional
@Service
public class SocialProfileService {
	
	@Autowired
	private SocialProfileRepository socialProfileRepository;
	
	@Autowired
	private ActorRepository actorRepository;
	
	public SocialProfile create(){
		return new SocialProfile();
	}
	
	public Collection<SocialProfile> findAll(){
		return socialProfileRepository.findAll();
	}
	
	public SocialProfile findOne(int socialProfileId){
		return socialProfileRepository.findOne(socialProfileId);
	}
	
	public SocialProfile save(SocialProfile socialProfile){
		return socialProfileRepository.save(socialProfile);
	}
	
	public void delete(SocialProfile socialProfile){
		socialProfileRepository.delete(socialProfile);
		final UserAccount actual = LoginService.getPrincipal();
		final Actor a = this.actorRepository.getActor(actual);
		a.getSocialProfiles().remove(socialProfile);
	}

	public void deleteLeave(final SocialProfile socialProfile) {
		this.socialProfileRepository.delete(socialProfile);

	}

	public void saveMyProfile(SocialProfile socialProfile) {
		final UserAccount actual = LoginService.getPrincipal();
		final Actor a = this.actorRepository.getActor(actual);
		final SocialProfile profile = this.socialProfileRepository.save(socialProfile);
		if (!a.getSocialProfiles().contains(socialProfile)) {
			final Collection<SocialProfile> profiles = a.getSocialProfiles();
			profiles.add(profile);
			a.setSocialProfiles(profiles);
		}
	}

	public Collection<SocialProfile> findMyProfiles() {
		Collection<SocialProfile> res = new ArrayList<SocialProfile>();
		final UserAccount actual = LoginService.getPrincipal();
		final Actor a = this.actorRepository.getActor(actual);
		if(a.getSocialProfiles()==null){
			a.setSocialProfiles(res);
			this.actorRepository.save(a);
			return res;
		}
		
		return a.getSocialProfiles();
	}


}
