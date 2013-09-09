/*
 *  Copyright (C) 2013 caryoscelus
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 *  Additional permission under GNU GPL version 3 section 7:
 *  If you modify this Program, or any covered work, by linking or combining
 *  it with Clojure (or a modified version of that library), containing parts
 *  covered by the terms of EPL 1.0, the licensors of this Program grant you
 *  additional permission to convey the resulting work. {Corresponding Source
 *  for a non-source form of such a combination shall include the source code
 *  for the parts of Clojure used as well as that of the covered work.}
 */

package lostsheep;

import lostsheep.creatures.Player;

import chlorophytum.story.*;
import chlorophytum.story.view.StoryStage;
import chlorophytum.map.view.ChloroMapStage;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MapScreen implements Screen, StoryScreen {
    protected final float TILES_NX = 25;
    protected final float TILES_NY = TILES_NX*3/4f;
    
    protected final float TILE_SIZE = 32;
    
    protected ChloroMapStage mapStage;
    protected StoryStage storyStage;
    
    protected boolean inited = false;
    
    public void init () {
        inited = true;
        
        mapStage = new ChloroMapStage();
        mapStage.init(TILE_SIZE, TILES_NX, TILES_NY);
        
        storyStage = new StoryStage();
        
        MapObjectViewFactory.init();
        
        Story.instance().screen = this;
        
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(storyStage);
        inputMultiplexer.addProcessor(mapStage);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }
    
    @Override
    public void show () {
        if (!inited) {
            init();
        }
    }
    
    @Override
    public void hide () {
    }
    
    @Override
    public void render (float dt) {
        update(dt);
        
        Gdx.gl.glClearColor(0.0f, 0.5f, 0.0f, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        
        mapStage.draw();
        
        if (storyStage.show) {
            storyStage.draw();
        }
    }
    
    @Override
    public void resize (int width, int height) {
    }
    
    @Override
    public void pause () {
    }
    
    @Override
    public void resume () {
    }
    
    @Override
    public void dispose () {
    }
    
    public void processInput (float dt) {
        float mdx = 0, mdy = 0;
        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            mdx += -1;
        }
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            mdx += 1;
        }
        if (Gdx.input.isKeyPressed(Keys.UP)) {
            mdy += 1;
        }
        if (Gdx.input.isKeyPressed(Keys.DOWN)) {
            mdy += -1;
        }
        
        Player pl = LostSheepGame.instance().player;
        pl.wantMove.x = mdx;
        pl.wantMove.y = mdy;
    }
    
    public void update (float dt) {
        Player pl = LostSheepGame.instance().player;
        
        if (!storyStage.show) {
            processInput(dt);
            pl.onMap.update(dt);
        }
        
        mapStage.setMap(pl.onMap);
        mapStage.setPosition(pl.position);
        mapStage.act(dt);
        
        storyStage.act(dt);
    }
    
    @Override
    public void showStory (StoryContext context) {
        storyStage.setContext(context);
    }
}
