package ca.mcgill.ecse223.block.statemachine.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import ca.mcgill.ecse223.block.statemachine.tests.p01.StartPauseResumeTests;
import ca.mcgill.ecse223.block.statemachine.tests.p02.MoveBallTests;
import ca.mcgill.ecse223.block.statemachine.tests.p03.BallHitsPaddleOrWallTests;
import ca.mcgill.ecse223.block.statemachine.tests.p04.BallHitsBlockTests;
import ca.mcgill.ecse223.block.statemachine.tests.p05.BallIsOutOfBoundsTests;
import ca.mcgill.ecse223.block.statemachine.tests.p06.ViewHallOfFameTests;
import ca.mcgill.ecse223.block.statemachine.tests.p07.TestGameTests;
import ca.mcgill.ecse223.block.statemachine.tests.p08.PublishGameTests;

@RunWith(Suite.class)

@Suite.SuiteClasses({
   StartPauseResumeTests.class,
   MoveBallTests.class,
   BallHitsPaddleOrWallTests.class,
   BallIsOutOfBoundsTests.class,
   BallHitsBlockTests.class,
   ViewHallOfFameTests.class,
   TestGameTests.class,
   PublishGameTests.class,
})
public class Block223BehaviorTestSuite {

}
