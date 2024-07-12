package works.akus.mediathief.objects;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Controller;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

@WebListener
@Controller
public class HttpSessionCollector implements HttpSessionListener {
    private static final Map<String, HttpSession> SESSIONS = new ConcurrentHashMap<>();

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        session.setAttribute("videosRequested", 0);
        SESSIONS.put(session.getId(), session);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        SESSIONS.remove(event.getSession().getId());
    }

    public static HttpSession byId(String sessionId) {
        return SESSIONS.get(sessionId);
    }
}
