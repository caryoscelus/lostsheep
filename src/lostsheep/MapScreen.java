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

import chlorophytum.map.ChloroMap;
import chlorophytum.story.*;

import com.badlogic.gdx.*;

public class MapScreen implements Screen, StoryScreen {
    ChloroMap map;
    
    boolean inited = false;
    
    public void init () {
        inited = true;
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
        
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
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
        map.dispose();
    }
    
    public void update (float dt) {
        
    }
    
    @Override
    public void showStory (StoryDialog dialogue) {
    }
    
    @Override
    public void hideStory () {
    }
}
