package co.kaush.rem;

import co.kaush.rem.db.DebugDbModule;
import dagger.Module;

@Module(
    addsTo = RemModule.class,
    includes = { DebugDbModule.class },
    overrides = true)
public class DebugRemModule {}