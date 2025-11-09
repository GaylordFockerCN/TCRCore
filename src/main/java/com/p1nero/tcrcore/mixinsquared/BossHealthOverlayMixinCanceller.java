package com.p1nero.tcrcore.mixinsquared;

import com.bawnorton.mixinsquared.api.MixinCanceller;

import java.util.List;

public class BossHealthOverlayMixinCanceller implements MixinCanceller {
    @Override
    public boolean shouldCancel(List<String> list, String s) {
        return s.equals("com.obscuria.obscureapi.mixin.client.BossHealthOverlayMixin");
    }
}
