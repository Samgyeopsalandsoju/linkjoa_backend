package com.samso.linkjoa.main.application.service;

import com.samso.linkjoa.clip.application.port.out.repository.ClipRepository;
import com.samso.linkjoa.main.application.port.out.TotalShareRepository;
import com.samso.linkjoa.main.presentation.port.in.MainInfoUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MainService implements MainInfoUseCase {

    private ClipRepository clipRepository;
    private TotalShareRepository totalShareRepository;
    @Override
    public long getClipTotalCount() { return clipRepository.count(); }
    @Override
    public long getShareTotalCount() {
        return totalShareRepository.findById(1L).get().getTotalCount();
    }
}
