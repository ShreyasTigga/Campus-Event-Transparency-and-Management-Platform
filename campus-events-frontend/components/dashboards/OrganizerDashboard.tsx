"use client";

import { useEffect, useState } from "react";
import { getEvents, deleteEvent, createEvent } from "../../services/api";

export default function OrganizerDashboard() {
  const [events, setEvents] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);
  const [creating, setCreating] = useState(false);

  const [form, setForm] = useState({
    title: "",
    description: "",
    location: "",
  });

  useEffect(() => {
    fetchEvents();
  }, []);

  async function fetchEvents() {
    try {
      setLoading(true);
      const res = await getEvents();
      setEvents(res?.content || []);
    } catch (err) {
      console.error("Failed to fetch events", err);
    } finally {
      setLoading(false);
    }
  }

  async function handleCreate(e: any) {
    e.preventDefault();

    if (!form.title || !form.description || !form.location) {
      alert("All fields are required");
      return;
    }

    try {
      setCreating(true);
      await createEvent(form);

      // Reset form
      setForm({
        title: "",
        description: "",
        location: "",
      });

      await fetchEvents();
    } catch (err) {
      alert("Failed to create event");
    } finally {
      setCreating(false);
    }
  }

  async function deleteEventHandler(id: number) {
    await deleteEvent(id);
    fetchEvents();
  }

  return (
    <div className="p-8 text-white">
      <h2 className="text-3xl font-bold mb-6">Organizer Dashboard</h2>

      {/* ---------------- CREATE EVENT FORM ---------------- */}
      <div className="bg-slate-800 p-6 rounded-xl mb-8 shadow-lg">
        <h3 className="text-xl font-semibold mb-4">
          Create New Event
        </h3>

        <form onSubmit={handleCreate} className="grid gap-4">
          <input
            type="text"
            placeholder="Event Title"
            className="p-2 rounded bg-slate-700"
            value={form.title}
            onChange={(e) =>
              setForm({ ...form, title: e.target.value })
            }
          />

          <textarea
            placeholder="Event Description"
            className="p-2 rounded bg-slate-700"
            value={form.description}
            onChange={(e) =>
              setForm({ ...form, description: e.target.value })
            }
          />

          <input
            type="text"
            placeholder="Location"
            className="p-2 rounded bg-slate-700"
            value={form.location}
            onChange={(e) =>
              setForm({ ...form, location: e.target.value })
            }
          />

          <button
            type="submit"
            disabled={creating}
            className="bg-green-600 px-4 py-2 rounded hover:bg-green-700 disabled:opacity-50"
          >
            {creating ? "Creating..." : "Create Event"}
          </button>
        </form>
      </div>

      {/* ---------------- EVENT LIST ---------------- */}
      {loading ? (
        <p>Loading events...</p>
      ) : events.length === 0 ? (
        <p className="text-gray-400">
          No events created yet.
        </p>
      ) : (
        <div className="grid gap-6">
          {events.map((event) => (
            <div
              key={event.id}
              className="bg-slate-800 p-6 rounded-xl"
            >
              <h3 className="text-xl font-semibold">
                {event.title}
              </h3>
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
      )}
    </div>
  );
}