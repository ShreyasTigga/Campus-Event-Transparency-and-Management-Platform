"use client";

import { useEffect, useState } from "react";
import { getEvents, deleteEvent } from "../../services/api";

export default function OrganizerDashboard() {
  const [events, setEvents] = useState([]);

  useEffect(() => {
    fetchEvents();
  }, []);

async function fetchEvents() {
  const res = await getEvents();
  setEvents(res.content);
}

async function deleteEventHandler(id: number) {
  await deleteEvent(id);
  fetchEvents();
}


  return (
    <div className="p-8 text-white">
      <h2 className="text-3xl font-bold mb-6">My Events</h2>

      <div className="grid gap-6">
        {events.map((event: any) => (
          <div key={event.id} className="bg-slate-800 p-6 rounded-xl">
            <h3 className="text-xl font-semibold">{event.title}</h3>
            <p>Status: {event.status}</p>

            {event.status === "PENDING" && (
              <button
                onClick={() => deleteEventHandler(event.id)}
                className="mt-4 bg-red-600 px-4 py-2 rounded"
              >
                Delete
              </button>
            )}
          </div>
        ))}
      </div>
    </div>
  );
}
